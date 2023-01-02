package awakenedOne.cards;

import awakenedOne.cards.tokens.Shadow;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.OnObtainCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToSelf;

public class AsShadow extends AbstractAwakenedCard implements OnObtainCard {
    public final static String ID = makeID(AsShadow.class.getSimpleName());
    // intellij stuff skill, self, rare, , , , , , 

    public AsShadow() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        isEthereal = true;
        cardsToPreview = new Shadow();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new IntangiblePlayerPower(p, 1));
    }

    @Override
    public void onObtainCard() {
        AbstractDungeon.effectsQueue.add(new ShowCardAndObtainEffect(new Shadow(), Settings.WIDTH / 3, Settings.HEIGHT / 2));
        AbstractDungeon.effectsQueue.add(new ShowCardAndObtainEffect(new Shadow(), (Settings.WIDTH / 3) * 2, Settings.HEIGHT / 2));
    }

    public void upp() {
        isEthereal = false;
    }
}