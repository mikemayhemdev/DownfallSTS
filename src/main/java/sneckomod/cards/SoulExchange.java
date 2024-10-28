package sneckomod.cards;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import sneckomod.actions.MuddleHandAction;

public class SoulExchange extends AbstractSneckoCard {

    public final static String ID = makeID("SoulExchange");

    //stupid intellij stuff SKILL, SELF, COMMON

    public SoulExchange() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        exhaust = true;
        SneckoMod.loadJokeCardImage(this, "SoulExchange.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
        atb(new MuddleHandAction());
        this.addToBot(new DiscardAction(p, p, 1, false));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            exhaust = false;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}