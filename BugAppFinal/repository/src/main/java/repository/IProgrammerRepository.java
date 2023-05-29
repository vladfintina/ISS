package repository;

import domain.Programmer;

public interface IProgrammerRepository extends Repository<Integer, Programmer> {
    public Programmer getAccount(String username, String password);
}
