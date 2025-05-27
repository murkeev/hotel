package murkeev.service;

import io.jsonwebtoken.*;
import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import murkeev.dto.RespondedUser;
import murkeev.model.User;
import murkeev.repo.UserRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import murkeev.config.JWTHelper;

import java.security.InvalidParameterException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional (rollbackOn = Exception.class)
public class UserService {
    private final UserRepo userRepo;


    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public ResponseEntity<List<User>> getUsers() { return ResponseEntity.ok(userRepo.findAll()); }

    public ResponseEntity<?> getUserData(String token) {
        if (!JWTHelper.isTokenCorrect(token)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is invalid");
        try {
            String email = JWTHelper.getEmailFromToken(token);
            return ResponseEntity.ok().body(createRespondedUser(userRepo.findByEmail(email).orElse(null)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Server error:(");
        }
    }


    public ResponseEntity<?> registration(User user) {
        if (user.getEmail() == null || user.getPassword() == null) return ResponseEntity.badRequest().body("User is null");

        List<User> users = userRepo.findAll();
        for (int i = 0; i < users.size(); i++) {
            if (Objects.equals(users.get(i).getEmail(), user.getEmail())) return ResponseEntity.badRequest().body("Email is already exists:(");
        }

        try {
            user.setPassword(this.hashPassword(user.getPassword()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Server error:(");
        }

        try {
            user.setToken(JWTHelper.createToken(user.getEmail()));
        }
        catch (Exception e) {
            return ResponseEntity.internalServerError().body("We are dead");
        }

        user = userRepo.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createRespondedUser(user));
    }

    public ResponseEntity<?> authorization(User user) {
        Optional<User> foundedUser = userRepo.findByEmail(user.getEmail());
        if (foundedUser.isEmpty()) return ResponseEntity.status(404).body("User not found");
        try {
            if (Objects.equals(foundedUser.get().getPassword(), hashPassword(user.getPassword()))) {
                user.setToken(JWTHelper.createToken(user.getEmail()));
                return ResponseEntity.accepted().body(createRespondedUser(user));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password is invalid");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Server error:(");
        }
    }

    private RespondedUser createRespondedUser(@Nullable User user) {
        if (user == null) return new RespondedUser("", "", "", "", "");
        return new RespondedUser(user.getName(), user.getSurname(), user.getPhone(), user.getEmail(), user.getToken());
    }
    private String hashPassword(String password) throws NoSuchAlgorithmException, InvalidParameterException {
        if (password.isEmpty()) throw new InvalidParameterException();

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = messageDigest.digest(password.getBytes());

        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : hashBytes) {
            stringBuilder.append(String.format("%02x", b));
        }
        return stringBuilder.toString();
    }

}
