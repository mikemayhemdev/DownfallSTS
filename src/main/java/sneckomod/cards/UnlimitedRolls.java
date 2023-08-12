package sneckomod.cards;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import expansioncontent.cardmods.EtherealMod;
import sneckomod.SneckoMod;
import sneckomod.powers.UnlimitedRollsPower;
import expansioncontent.cardmods.ExhaustMod;

public class UnlimitedRolls extends AbstractSneckoCard {

    public final static String ID = makeID("UnlimitedRolls");

    //stupid intellij stuff POWER, SELF, RARE

    public UnlimitedRolls() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        AbstractCard q = new SoulRoll();
        CardModifierManager.addModifier(q, new EtherealMod());
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