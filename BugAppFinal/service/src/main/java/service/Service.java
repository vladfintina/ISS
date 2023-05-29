package service;


import domain.Bug;
import domain.Programmer;
import domain.Tester;
import repository.IBugRepository;
import repository.IProgrammerRepository;
import repository.ITesterRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Service {
    private IProgrammerRepository programmerRepository;
    private ITesterRepository testerRepository;
    private IBugRepository bugRepository;

    private Map<Integer, IBugsObserver> loggedTesters;

    private Map<Integer, IBugsObserver> loggedProgrammers;

    public Service(IProgrammerRepository programmerRepository, ITesterRepository testerRepository, IBugRepository bugRepository)
    {
        this.programmerRepository = programmerRepository;
        this.testerRepository = testerRepository;
        this.bugRepository = bugRepository;
        loggedTesters = new HashMap<>();
        loggedProgrammers = new HashMap<>();
    }

    public Tester loginVerificationTester(String username, String password, IBugsObserver client) throws BugException {
        Tester tester = null;
        tester = testerRepository.getAccount(username, password);
        if(tester != null)
        {
            if(loggedTesters.get(tester.getID()) != null)
                throw new BugException("User already logged in!");
            loggedTesters.put(tester.getID(), client);
            return tester;
        }

        throw new BugException("Authentication failed!");
    }

    public Programmer loginVerificationProgrammer(String username, String password, IBugsObserver client) throws BugException{
        Programmer programmer = programmerRepository.getAccount(username, password);
        if(programmer != null){
            if(loggedProgrammers.get(programmer.getID()) != null)
                throw new BugException("User already logged in!");
            loggedProgrammers.put(programmer.getID(), client);
            return programmer;
        }

        throw new BugException("Authentication failed!");
    }

    public List<Bug> getAllBugs()
    {
        System.out.println("getAllBugs: Service");
        Iterable<Bug> bugIterable = bugRepository.findAll();
        List<Bug> allBugs = new ArrayList<>();
        for(var bug: bugIterable)
        {
            allBugs.add(bug);
        }
        return allBugs;
    }

    public  List<Bug> getBugsFilteredByStatus(String status){
        System.out.println("getAllBugsFilteredByStatus: Service");
        Iterable<Bug> bugIterable = bugRepository.findAllByStatus(status);
        List<Bug> allBugs = new ArrayList<>();
        for(var bug: bugIterable)
        {
            allBugs.add(bug);
        }
        return allBugs;
    }

    public  List<Bug> getBugsFilteredByRiskLevel(String riskLevel){
        System.out.println("getAllBugsFilteredByRiskLevel: Service");
        Iterable<Bug> bugIterable = bugRepository.findAllByRiskLevel(riskLevel);
        List<Bug> allBugs = new ArrayList<>();
        for(var bug: bugIterable)
        {
            allBugs.add(bug);
        }
        return allBugs;
    }

    public void addBug(Bug bug) throws BugException {
        var verifyBug = bugRepository.findOneByTitle(bug.getTitle());
        if( verifyBug!= null)
            throw new BugException("This bug already exists!");
        bugRepository.add(bug);

        for (Integer key : loggedTesters.keySet()) {
            IBugsObserver receiver = loggedTesters.get(key);
            receiver.updateReceived();
        }

        for (Integer key : loggedProgrammers.keySet()) {
            IBugsObserver receiver = loggedProgrammers.get(key);
            receiver.updateReceived();
        }
    }

    public void updateBugStatus(Bug modifiedBug) throws BugException{
        bugRepository.update(modifiedBug);

        for (Integer key : loggedTesters.keySet()) {
            IBugsObserver receiver = loggedTesters.get(key);
            receiver.updateReceived();
        }

        for (Integer key : loggedProgrammers.keySet()) {
            IBugsObserver receiver = loggedProgrammers.get(key);
            receiver.updateReceived();
        }

    }
}
