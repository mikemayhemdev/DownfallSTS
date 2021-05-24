package collector.util;

import basemod.abstracts.CustomSavable;
import collector.TorchChar;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;

public class AllyAbilities implements CustomSavable<ArrayList<AbstractCard>> {
    ArrayList<AbstractCard> SavedAbilities = new ArrayList<>();
    @Override
    public ArrayList<AbstractCard> onSave() {
        return SavedAbilities = TorchChar.Abilities;
    }

    @Override
    public void onLoad(ArrayList<AbstractCard> abstractCards) {
        TorchChar.Abilities = SavedAbilities;
    }
}
