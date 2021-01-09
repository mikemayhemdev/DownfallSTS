package expansioncontent.cards.deprecated;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import downfall.util.CardIgnore;
import expansioncontent.cards.AbstractExpansionCard;
import expansioncontent.expansionContentMod;
import guardian.powers.DontLeaveDefensiveModePower;

@CardIgnore
public class DefensiveMode extends AbstractExpansionCard {
    public final static String ID = makeID("DefensiveMode");

    private static final int BLOCK = 5;
    private static final int UPGRADE_BLOCK = 5;
    private static final int MAGIC = 2;
    private static final int UPGRADE_MAGIC = 1;

    public DefensiveMode() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);

        tags.add(expansionContentMod.STUDY_GUARDIAN);
        tags.add(expansionContentMod.STUDY);

        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        atb(new ChangeStanceAction(guardian.stances.DefensiveMode.STANCE_ID));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DontLeaveDefensiveModePower(AbstractDungeon.player, magicNumber), magicNumber));

//        atb(new ApplyPowerAction(p, p, new ThornsPower(p, this.magicNumber), this.magicNumber));
//        atb(new ApplyPowerAction(p, p, new NextTurnBlockPower(p, this.block), this.block));


    }

    public void upgrade() {
        if (!upgraded) {
            upgradeMagicNumber(1);
        }
    }

}