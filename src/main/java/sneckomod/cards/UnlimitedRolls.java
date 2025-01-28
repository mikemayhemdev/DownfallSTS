package sneckomod.cards;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.CardIgnore;
import expansioncontent.cardmods.ExhaustMod;
import sneckomod.SneckoMod;
import sneckomod.powers.UnlimitedRollsPower;

@Deprecated
@CardIgnore
public class UnlimitedRolls extends AbstractSneckoCard {

    public final static String ID = makeID("UnlimitedRolls");

    //stupid intellij stuff POWER, SELF, RARE

    public UnlimitedRolls() {
        super(ID, 1, CardType.POWER, CardRarity.SPECIAL, CardTarget.SELF);
        //     super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        AbstractCard q = new SoulRoll();
        CardModifierManager.addModifier(q, new ExhaustMod());
        q.initializeDescription();
        cardsToPreview = q;
        SneckoMod.loadJokeCardImage(this, "UnlimitedRolls.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new UnlimitedRollsPower());
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            this.isInnate = true;
            initializeDescription();
        }
    }
}