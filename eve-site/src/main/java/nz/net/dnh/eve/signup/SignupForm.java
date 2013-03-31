package nz.net.dnh.eve.signup;

import org.hibernate.validator.constraints.*;

import nz.net.dnh.eve.account.Account;

public class SignupForm {

	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
	private static final String EMAIL_MESSAGE = "{email.message}";

	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	private String name;
	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	@Email(message = SignupForm.EMAIL_MESSAGE)
	private String email;
	@NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	private String password;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Account createAccount() {
		Account account = new Account(getEmail(), getPassword(), "ROLE_USER");
		account.setName(getName());
		return account;
	}
}
