package lightsaberInventory.Model;


/** InHouse class extends the abstract Part class, adding "machineID" attribute and defining methods.
 @author Benji Magnelli by the grace of God
 */

public class InHouse extends Part {

    private int machineID;

    /** Constructor for InHouse instances. Constructor extends the abstract Part class adding "machineID" attribute.
     @param id system defined
     @param name string name of the product
     @param price price in USD
     @param stock amount held in inventory
     @param min minimum inventory
     @param max maximum inventory
     @param machineId the UUID of the machine used to build this part
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineID = machineId;
    };


    /** Setter method for MachineId. SetMachine ID updates machineID for InHouse Parts.
     @param machineID the UUID of the machine used to build this part
     */
    public void setMachineId(int machineID) {
        this.machineID = machineID;
    }

    /** Getter method for MachineId. GetMachineID returns machineID for InHouse Parts.
     @return the UUID of the machine used to build this part
     */
    public int getMachineId() {
        return machineID;
    };


}
