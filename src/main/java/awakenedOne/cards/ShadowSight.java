package awakenedOne.cards;

import awakenedOne.cards.tokens.Shadow;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.OnObtainCard;
import com.megacrit.cardcrawl.actions.defect.SeekAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.atb;

public class ShadowSight extends AbstractAwakenedCard implements OnObtainCard {
    public final static String ID = makeID(ShadowSight.class.getSimpleName());
    // intellij stuff skill, self, uncommon, , , , , , 

    public ShadowSight() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        exhaust = true;
        cardsToPreview = new Shadow();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SeekAction(1));
    }

    @Override
    public void onObtainCard() {
        AbstractDungeon.effectsQueue.add(new ShowCardAndObtainEffect(new Shadow(), Settings.WIDTH / 2, Settings.HEIGHT / 2));
    }

    public void upp() {
        selfRetain = true;
    }
}