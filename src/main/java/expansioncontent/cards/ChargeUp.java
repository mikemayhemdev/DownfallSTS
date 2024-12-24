package expansioncontent.cards;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import downfall.util.CardIgnore;
import expansioncontent.expansionContentMod;
import hermit.actions.ReduceDebuffsAction;
import slimebound.powers.NextTurnGainStrengthPower;

public class ChargeUp extends AbstractExpansionCard {
    public final static String ID = makeID("ChargeUp");

    private static final int BLOCK = 20;
    private static final int UPGRADE_BLOCK = 10;
    private static final int MAGIC = 2;

    public ChargeUp() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        //todo skill bg instead of attack bg
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_guardian.png", "expansioncontentResources/images/1024/bg_boss_guardian.png");

        tags.add(expansionContentMod.STUDY_GUARDIAN);
        tags.add(expansionContentMod.STUDY);
        baseMagicNumber = magicNumber = MAGIC;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ReduceDebuffsAction(AbstractDungeon.player, magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.exhaust = false;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

}


