package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

import static com.mindhub.homebanking.models.TransactionType.CREDIT;
import static com.mindhub.homebanking.models.TransactionType.DEBIT;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
		return (args) -> {
			// save a couple of customers
			Client client = new Client("Melba", "Morel","melba@mindhub.com", passwordEncoder.encode("melbita123"));
			Client client1 = new Client("Cristiano", "Ronaldo", "cr7@gmail.com", passwordEncoder.encode("subcampeon"));
			Client admin = new Client("Lionel Andres", "Messi", "admin@gmail.com", passwordEncoder.encode("goat"));
			clientRepository.save(client);
			clientRepository.save(client1);
			clientRepository.save(admin);

			Account account = new Account("VIN001", 5000.0, LocalDateTime.now(),client,true,AccountType.SAVING);
			Account account1 = new Account("VIN002", 7500.0, LocalDateTime.now().plusDays(1),AccountType.CURRENT,true);
			client.addAccount(account1);
			accountRepository.save(account);
			accountRepository.save(account1);
			clientRepository.save(client);
			Account account2 = new Account("VIN003", 10000, LocalDateTime.now(), client1,true,AccountType.CURRENT);
			Account account3 = new Account("VIN004", 17500, LocalDateTime.now().plusDays(4),client1,true,AccountType.CURRENT);

			accountRepository.save(account2);
			accountRepository.save(account3);

			Transaction transaction0 = new Transaction(CREDIT, 500.0, "Credit Transaction", LocalDateTime.now(),account);
			Transaction transaction1 = new Transaction(DEBIT,-800,"Debit Transaction", LocalDateTime.now(),account);
			transactionRepository.save(transaction0);
			transactionRepository.save(transaction1);

			Loan loan = new Loan("Hipotecario", 500000.0, List.of(12,24,36,48,60));
			Loan loan1 = new Loan("Personal", 100000, List.of(6,12,24));
			Loan loan2 = new Loan("Automotriz", 300000, List.of(6,12,24,36));
			loanRepository.save(loan);
			loanRepository.save(loan1);
			loanRepository.save(loan2);

			ClientLoan clientLoan = new ClientLoan(400000, 60, client,loan);
			ClientLoan clientLoan1 = new ClientLoan(50000, 12, client,loan1);
			ClientLoan clientLoan2 = new ClientLoan(100000, 24, client1,loan1);
			ClientLoan clientLoan3 = new ClientLoan(200000, 36, client1,loan2);
			clientLoanRepository.save(clientLoan);
			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);
			clientLoanRepository.save(clientLoan3);

			Card cardGold = new Card(client.getFirstName() + " " + client.getLastName(), CardType.DEBIT, CardColor.GOLD,"4568 4871 4569 1234", 301,LocalDateTime.now(),LocalDateTime.now(),  client,true);
			Card cardTitanium = new Card(client.getFirstName() + " " + client.getLastName(),CardType.CREDIT, CardColor.TITANIUM, "4540 5897 2154 6985", 222, LocalDateTime.now().plusYears(5),LocalDateTime.now() , client,true);
			Card cardSilver = new Card(client1.getFirstName() + " " + client1.getLastName(), CardType.CREDIT, CardColor.SILVER, "4580 5874 1254 3564", 879, LocalDateTime.now(), LocalDateTime.now().plusYears(5), client1,true);
			cardRepository.save(cardGold);
			cardRepository.save(cardTitanium);
			cardRepository.save(cardSilver);
		};
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
}