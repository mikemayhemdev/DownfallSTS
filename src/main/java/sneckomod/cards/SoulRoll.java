package sneckomod.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import sneckomod.actions.MuddleHandAction;

public class SoulRoll extends AbstractSneckoCard {

    public final static String ID = makeID("SoulRoll");

    //stupid intellij stuff SKILL, SELF, COMMON

    public SoulRoll() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        tags.add(SneckoMod.SNEKPROOF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new MuddleHandAction());
        if (upgraded) upgradeAction(p,m);
    }

    public void upgradeAction(AbstractPlayer p, AbstractMonster m){
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}