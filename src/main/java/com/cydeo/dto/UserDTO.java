package com.cydeo.dto;

import com.cydeo.enums.Gender;
import lombok.*;

import javax.validation.constraints.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
public class UserDTO {

    private Long id;

    @Setter
    @NotBlank
    @Size(max = 15, min = 2)
    private String firstName;

    @Setter
    @NotBlank
    @Size(max = 15, min = 2)
    private String lastName;

    @Setter
    @NotBlank
    @Email
    private String userName;

    @NotBlank
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,}")
    private String passWord;

    @NotNull
    private String confirmPassWord;

    @Setter
    private boolean enabled;

    @Setter
    @NotBlank
    @Pattern(regexp = "^\\d{10}$")
    private String phone;

    @Setter
    @NotNull
    private RoleDTO role;

    @Setter
    @NotNull
    private Gender gender;

    public void setPassWord(String passWord) {
        this.passWord = passWord;
        checkConfirmPassWord();
    }

    public void setConfirmPassWord(String confirmPassWord) {
        this.confirmPassWord = confirmPassWord;
        checkConfirmPassWord();
    }

    private void checkConfirmPassWord() {
        if(this.passWord == null || this.confirmPassWord == null){
            return;
        }else if(!this.passWord.equals(confirmPassWord)){
            this.confirmPassWord = null;
        }
    }

}
