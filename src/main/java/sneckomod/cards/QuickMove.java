package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import sneckomod.SneckoMod;

public class QuickMove extends AbstractSneckoCard {

    public static final String ID = SneckoMod.makeID("QuickMove");

    private static final int BASE_BLOCK = 7;
    private static final int UPG_BLOCK = 3;
    private static final int MAGIC = 1;
    //private static final int UPG_MAGIC = 1;

    public QuickMove() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        baseBlock = BASE_BLOCK;
        tags.add(SneckoMod.OVERFLOW);
        SneckoMod.loadJokeCardImage(this, "QuickMove.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        if (isOverflowActive(this)) {
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if ((!monster.isDead) && (!monster.isDying) && !monster.halfDead) {
                    atb(new ApplyPowerAction(monster, p, new VulnerablePower(monster, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
                }
            }
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPG_BLOCK);
        }
    }
}
