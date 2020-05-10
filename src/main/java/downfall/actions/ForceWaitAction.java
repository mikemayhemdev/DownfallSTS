package downfall.actions;

import com.megacrit.cardcrawl.actions.utility.WaitAction;

// Forces the wait time, even in Fast Mode
public class ForceWaitAction extends WaitAction
{
    public ForceWaitAction(float setDur)
    {
        super(setDur);
        duration = setDur;
    }
}