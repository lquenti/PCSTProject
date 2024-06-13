package com.softwaretesting.testing.dao;

import com.softwaretesting.testing.model.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;


import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    TestEntityManager entityManager;

    private static final String EXAMPLE_NAME = "Max Mustermann";
    private static final String EXAMPLE_USERNAME = "mmuster";
    private static final String EXAMPLE_PHONE_NUMBER = "+49123456789";


    /* We test each part of CRUD */

    /* General note: We explicitly only test whether the database works. Any controller-related logical
     * rejection criteria are not tested.
     */

    /* Create: */

    /* Every user should have a username */
    @Test
    public void emptyUsernameFails() {
        Customer customer = new Customer();
        customer.setName(EXAMPLE_NAME);
        customer.setPhoneNumber(EXAMPLE_PHONE_NUMBER);
        customerRepository.save(customer);

        // Since DB operations are transactional we have to
        // actively flush for Spring to notice that there is a violation
        assertThrows(
                ConstraintViolationException.class,
                () -> entityManager.flush()
        );
    }

    /* Every user should have a name */
    @Test
    public void emptyNameFails() {
        Customer customer = new Customer();
        customer.setUserName(EXAMPLE_USERNAME);
        customer.setPhoneNumber(EXAMPLE_PHONE_NUMBER);
        customerRepository.save(customer);

        assertThrows(
                ConstraintViolationException.class,
                () -> entityManager.flush()
        );
    }

    /* Every user should have a phone number */
    @Test
    public void emptyPhoneNumberFails() {
        Customer customer = new Customer();
        customer.setName(EXAMPLE_NAME);
        customer.setUserName(EXAMPLE_USERNAME);
        customerRepository.save(customer);

        assertThrows(
                ConstraintViolationException.class,
                () -> entityManager.flush()
        );
    }

    /* The id has to be unique */
    @Test
    public void sameIdTwiceFails() {
        Customer customer = new Customer();
        customer.setUserName(EXAMPLE_USERNAME);
        customer.setPhoneNumber(EXAMPLE_PHONE_NUMBER);
        customerRepository.save(customer);

        Customer customer2 = new Customer(
                customer.getId(),
                "mmuster2",
                "Max Mustermann",
                "+491234567899"
        );
        customerRepository.save(customer2);

        assertThrows(
                PersistenceException.class,
                () -> entityManager.flush()
        );
    }

    /* A good Customer should be able to be created */
    @Test
    public void creationWorks() {
        Customer customer = new Customer();
        customer.setName(EXAMPLE_NAME);
        customer.setUserName(EXAMPLE_USERNAME);
        customer.setPhoneNumber(EXAMPLE_PHONE_NUMBER);
        customerRepository.save(customer);

        assertDoesNotThrow(
                () -> entityManager.flush()
        );
    }

    /* Read */

    /* A properly created user should be able to be instantly recieved back */
    @Test
    public void readJustCreatedUserByUsernameWorks() {
        Customer customer = new Customer();
        customer.setName(EXAMPLE_NAME);
        customer.setUserName(EXAMPLE_USERNAME);
        customer.setPhoneNumber(EXAMPLE_PHONE_NUMBER);
        customerRepository.save(customer);

        /* this implicitly triggers a flush btw */
        var opt = customerRepository.findByUserName(EXAMPLE_USERNAME);

        /* first check whether the optional is not none */
        assertTrue(opt.isPresent());

        /* next extract the optional and compare the ID */
        assertEquals(opt.get().getId(), customer.getId());
    }
    @Test
    public void readJustCreatedUserByNumberWorks() {
        Customer customer = new Customer();
        customer.setName(EXAMPLE_NAME);
        customer.setUserName(EXAMPLE_USERNAME);
        customer.setPhoneNumber(EXAMPLE_PHONE_NUMBER);
        customerRepository.save(customer);

        var opt = customerRepository.selectCustomerByPhoneNumber(EXAMPLE_PHONE_NUMBER);

        assertTrue(opt.isPresent());
        assertEquals(opt.get().getId(), customer.getId());
    }

    /* Reading a not yet created user should fail */
    @Test
    public void readNotExistingUserByUsernameFails() {
        /* This works because the mock object does not keep state between test cases
        *  If this was not given, we would have to ues another phone number
        * */
        var opt = customerRepository.selectCustomerByPhoneNumber(EXAMPLE_PHONE_NUMBER);
        assertTrue(opt.isEmpty());
    }
    @Test
    public void readNotExistingUserByNumberFails() {
        var opt = customerRepository.findByUserName(EXAMPLE_USERNAME);
        assertTrue(opt.isEmpty());
    }

    /* Update: */

    /* Successfully update the name */
    @Test
    public void updateNameJustCreatedUserWorks() {
        Customer customer = new Customer();
        customer.setName(EXAMPLE_NAME);
        customer.setUserName(EXAMPLE_USERNAME);
        customer.setPhoneNumber(EXAMPLE_PHONE_NUMBER);
        customerRepository.save(customer);
        entityManager.flush();

        /* Lets say he married */
        customer.setName("Max Musterpaar");
        customerRepository.save(customer);

        /* Check that it actually changed
         * I think this test case is kinda weird, because we are
         * still in the same transaction.
         *
         * But as a best case effort, its oaky.
         */
        var optional = customerRepository.findByUserName(EXAMPLE_USERNAME);
        assertTrue(optional.isPresent());
        Customer updatedCustomer = optional.get();

        assertEquals("Max Musterpaar", updatedCustomer.getName());
    }

    /* Successfully update the username */
    @Test
    public void updateUsernameJustCreatedUserWorks() {
        Customer customer = new Customer();
        customer.setName(EXAMPLE_NAME);
        customer.setUserName(EXAMPLE_USERNAME);
        customer.setPhoneNumber(EXAMPLE_PHONE_NUMBER);
        customerRepository.save(customer);
        entityManager.flush();

        customer.setUserName("mmuster2");
        customerRepository.save(customer);

        var optional = customerRepository.findByUserName("mmuster2");
        assertTrue(optional.isPresent());
        Customer updatedCustomer = optional.get();
        assertEquals("mmuster2", updatedCustomer.getUserName());
    }

    /* Successfully update the phone number */
    @Test
    public void updatePhoneJustCreatedUserWorks() {
        Customer customer = new Customer();
        customer.setName(EXAMPLE_NAME);
        customer.setUserName(EXAMPLE_USERNAME);
        customer.setPhoneNumber(EXAMPLE_PHONE_NUMBER);
        customerRepository.save(customer);
        entityManager.flush();

        customer.setPhoneNumber("+49987654321");
        customerRepository.save(customer);

        var optional = customerRepository.findByUserName(EXAMPLE_USERNAME);
        assertTrue(optional.isPresent());
        Customer updatedCustomer = optional.get();
        assertEquals("+49987654321", updatedCustomer.getPhoneNumber());
    }

    /* Fail if the new name is null */
    @Test
    public void updateNameToNullFails() {
        Customer customer = new Customer();
        customer.setName(EXAMPLE_NAME);
        customer.setUserName(EXAMPLE_USERNAME);
        customer.setPhoneNumber(EXAMPLE_PHONE_NUMBER);
        customerRepository.save(customer);
        entityManager.flush();

        customer.setName(null);

        assertThrows(
                ConstraintViolationException.class,
                () -> entityManager.flush()
        );
    }

    /* Fail if the new username is null */
    @Test
    public void updateUsernameToNullFails() {
        Customer customer = new Customer();
        customer.setName(EXAMPLE_NAME);
        customer.setUserName(EXAMPLE_USERNAME);
        customer.setPhoneNumber(EXAMPLE_PHONE_NUMBER);
        customerRepository.save(customer);
        entityManager.flush();

        customer.setUserName(null);

        assertThrows(
                ConstraintViolationException.class,
                () -> entityManager.flush()
        );
    }

    /* Fail if the new phone number is null */
    @Test
    public void updatePhoneToNullFails() {
        Customer customer = new Customer();
        customer.setName(EXAMPLE_NAME);
        customer.setUserName(EXAMPLE_USERNAME);
        customer.setPhoneNumber(EXAMPLE_PHONE_NUMBER);
        customerRepository.save(customer);
        entityManager.flush();

        customer.setPhoneNumber(null);

        assertThrows(
                ConstraintViolationException.class,
                () -> entityManager.flush()
        );
    }

    /* Delete */

    /* Delete a just created user */
    @Test
    public void deletionOfExistingWorks() {
        Customer customer = new Customer();
        customer.setName(EXAMPLE_NAME);
        customer.setUserName(EXAMPLE_USERNAME);
        customer.setPhoneNumber(EXAMPLE_PHONE_NUMBER);
        customerRepository.save(customer);
        entityManager.flush();

        customerRepository.delete(customer);
        entityManager.flush();

        /* Verify that deletion worked */
        var optional = customerRepository.findByUserName(EXAMPLE_USERNAME);

        /* This time isEmpty since we do not expect a user to be there anymore! */
        assertTrue(optional.isEmpty());
    }

    /* Fail if the user was not created before */
    @Test
    public void deletionOfNonExistingWorks() {
        Customer customer = new Customer();
        customer.setName(EXAMPLE_NAME);
        customer.setUserName(EXAMPLE_USERNAME);
        customer.setPhoneNumber(EXAMPLE_PHONE_NUMBER);
        /* NOTE THAT WE DID NOT SAVE IT */

        assertDoesNotThrow(
                () -> customerRepository.delete(customer)
        );
    }

}
