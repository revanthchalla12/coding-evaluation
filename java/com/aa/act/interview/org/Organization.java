package com.aa.act.interview.org;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public abstract class Organization {

    private Position root;

    // LHM isn't necessary but using it to maintain hierarchy
    private final Map<String, Position> positions = new LinkedHashMap<>();

    private int idx = 0;
    
    public Organization() {
        root = createOrganization();
        updatePositions();  //updates positions map
    }
    
    protected abstract Position createOrganization();
    
    /**
     * hire the given person as an employee in the position that has that title
     * 
     * @param person
     * @param title
     * @return the newly filled position or empty if no position has that title
     */
    public Optional<Position> hire(Name person, String title) {
        //your code here
        if (positions.containsKey(title)) {
            final Position position = positions.get(title);
            position.setEmployee(Optional.of(new Employee(++idx, person)));
            return Optional.of(position);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public String toString() {
        return printOrganization(root, "");
    }
    
    private String printOrganization(Position pos, String prefix) {
        StringBuffer sb = new StringBuffer(prefix + "+-" + pos.toString() + "\n");
        for(Position p : pos.getDirectReports()) {
            sb.append(printOrganization(p, prefix + "  "));
        }
        return sb.toString();
    }

    /**
     * updates map with all positions in the org
     */
    private void updatePositions() {
        updateAllPositionsRecursive(root, positions);
    }

    /**
     * starts at the root & adds position title to existing map
     *
     * @param position
     * @param positions
     */
    private void updateAllPositionsRecursive(final Position position, final Map<String, Position> positions) {
        if (position != null) {
            positions.put(position.getTitle(), position);
            if (position.getDirectReports() != null) {
                for (final Position dp : position.getDirectReports()) {
                    updateAllPositionsRecursive(dp, positions);    //recursively add all positions of direct reports
                }
            }
        }
    }

}
