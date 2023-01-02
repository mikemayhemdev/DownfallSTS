package awakenedOne.cards;

import awakenedOne.cards.tokens.Shadow;
import awakenedOne.powers.CrowsCallPower;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.OnObtainCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToSelf;

public class CrowsCall extends AbstractAwakenedCard {
    public final static String ID = makeID(CrowsCall.class.getSimpleName());
    // intellij stuff power, self, uncommon, , , , , 4, 2

    public CrowsCall() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
        cardsToPreview = new Shadow();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new CrowsCallPower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(2);
    }
}