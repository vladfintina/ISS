package repository;

import domain.Tester;

public interface ITesterRepository extends Repository<Integer, Tester> {

    public Tester getAccount(String username, String password);
}
