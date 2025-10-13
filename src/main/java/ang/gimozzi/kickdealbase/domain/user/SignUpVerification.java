package ang.gimozzi.kickdealbase.domain.user;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Random;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SignUpVerification {

    @Id
    private String email;

    private String code;

    private boolean isVerified;

    public SignUpVerification(String email){
        this.email = email;
        this.code = generateCode();
        this.isVerified = false;
    }

    private String generateCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    public void verify(){
        isVerified = true;
    }


    public void validateCode(String requestCode){
        if(!code.equals(requestCode)){
            throw new IllegalArgumentException("Invalid request code");
        }
    }

    public void validateVerified(){
        if(!isVerified){
            throw new IllegalArgumentException("Invalid verification code");
        }
    }

}

