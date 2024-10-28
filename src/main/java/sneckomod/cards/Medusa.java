package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import sneckomod.SneckoMod;

public class Medusa extends AbstractSneckoCard {

    public static final String ID = SneckoMod.makeID("Medusa");

    private static final int BLOCK = 4;
    private static final int UPG_BLOCK = 2;
    private static final int WEAK = 1;
    private static final int UPG_WEAK = 1;
    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 1;
    int weak = WEAK;
    public Medusa() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        SneckoMod.loadJokeCardImage(this, "Medusa.png");
        this.tags.add(SneckoMod.OVERFLOW);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        if (isOverflowActive(this)) {
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                addToBot(new ApplyPowerAction(monster, p, new WeakPower(monster, weak, false), weak, true, AbstractGameAction.AttackEffect.NONE));
                addToBot(new ApplyPowerAction(monster, p, new PoisonPower(monster, p, magicNumber), magicNumber, true, AbstractGameAction.AttackEffect.POISON));
            }
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPG_BLOCK);
            upgradeMagicNumber(UPG_MAGIC);
            weak = (weak + UPG_WEAK);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
