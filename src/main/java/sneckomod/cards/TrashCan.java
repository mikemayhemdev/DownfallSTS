package sneckomod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import sneckomod.powers.TrashCanPower;

public class TrashCan extends AbstractSneckoCard {

    public final static String ID = makeID("TrashCan");

    //stupid intellij stuff POWER, SELF, UNCOMMON

    public TrashCan() {
        super(ID, 0, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        isInnate = false;
        SneckoMod.loadJokeCardImage(this, "TrashCan.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new TrashCanPower(p, p, 1), 1));
    }

    public void upgrade() {
        if (!upgraded) {
            isInnate = true;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
            upgradeName();
        }
    }
}