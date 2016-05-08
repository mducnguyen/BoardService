package main.java.handlers.responseobjects;

import lombok.Data;
import main.java.logiccontroller.Throw;
import main.java.models.repositories.RollRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DucNguyenMinh
 * @since 05/05/16
 */
@Data
public class ThrowsResponseObj
{
    private List<ThrowResponseObj> Throws;

    public ThrowsResponseObj(List<Throw> thrs)
    {

        this.Throws = new ArrayList<>();

        RollResponseObj rollRes1 = null;
        RollResponseObj rollRes2 = null;

        for (Throw thr : thrs
                ) {
            rollRes1 = new RollResponseObj(thr.getRoll1().getNumber());
            rollRes2 = new RollResponseObj(thr.getRoll2().getNumber());
            ThrowResponseObj rollsResponseObj = new ThrowResponseObj(rollRes1, rollRes2);
            this.Throws.add(rollsResponseObj);
        }
    }
}
