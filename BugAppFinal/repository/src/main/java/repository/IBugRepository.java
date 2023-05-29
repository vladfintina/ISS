package repository;

import domain.Bug;

public interface IBugRepository extends Repository<Integer, Bug> {
    public Bug findOneByTitle(String title);

    public Iterable<Bug> findAllByStatus(String status);

    public Iterable<Bug> findAllByRiskLevel(String riskLevel);
}
