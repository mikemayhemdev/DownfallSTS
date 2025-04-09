package collector.cards;

import basemod.helpers.BaseModCardTags;
import collector.effects.GreenThirdEyeEffect;
import collector.powers.DarkLordFormPower;
import collector.powers.DarkLordFormPowerPlus;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;
import static collector.util.Wiz.atb;

public class DarkLordForm extends AbstractCollectorCard {
    public final static String ID = makeID(DarkLordForm.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , , 

    public DarkLordForm() {
        super(ID, 4, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        cardsToPreview = new YouAreMine();
        tags.add(BaseModCardTags.FORM);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new VFXAction(new GreenThirdEyeEffect(p.hb.cX, p.hb.cY)));
        if (upgraded){
            applyToSelf(new DarkLordFormPowerPlus());

        } else {
            applyToSelf(new DarkLordFormPower());
        }
    }

    public void upp() {
        uDesc();
        cardsToPreview.upgrade();
    }
}