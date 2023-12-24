package dev.Innocent.message.DTO;

/**
 * @param accountNumber
 * @param name
 * @param email
 * @param mobileNumber
 */
public record AccountsMsgDTO(Long accountNumber, String name, String email, String mobileNumber) {
}
