package domain;


public class Entity<TID> {
    public TID Id;

    /**
     *
     * @return id of the entity
     */
    public TID getID()
    {
        return Id;
    }

    /**
     * set the id
     * @param newID
     */
    public void setID(TID newID)
    {
        Id = newID;
    }
}
