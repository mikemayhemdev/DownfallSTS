package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.actions.RandomDamageAction;
import sneckomod.powers.MuddleDrawnCardsPower;

public class RainOfDice extends AbstractSneckoCard {

    public final static String ID = makeID("RainOfDice");

    //stupid intellij stuff ATTACK, ALL, COMMON

    private static final int DAMAGE = 4;
    private static final int UPG_DAMAGE = 1;

    private static final int MAGIC = 4;
    private static final int UPG_MAGIC = 1;

    public RainOfDice() {
        super(ID,  1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        baseSilly = silly = 2;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        int CURRENT_SILLY = baseSilly;
        int CURRENT_DAMAGE = baseDamage;
        baseDamage = CURRENT_SILLY;
        super.applyPowers();
        silly = damage;
        isSillyModified = damage != baseDamage;

        baseDamage = CURRENT_DAMAGE;
        super.applyPowers();
    }

    @Override
    public void calculateCardDamage(final AbstractMonster m) {
        int CURRENT_SILLY = baseSilly;
        int CURRENT_DAMAGE = baseDamage;
        baseDamage = CURRENT_SILLY;
        super.calculateCardDamage(m);
        silly = damage;
        isSillyModified = damage != baseDamage;

        baseDamage = CURRENT_DAMAGE;
        super.calculateCardDamage(m);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new RandomDamageAction(AbstractDungeon.getMonsters().getRandomMonster(true), silly, damage, getRandomNum(2, magicNumber), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        applyToSelf(new MuddleDrawnCardsPower(getRandomNum(2, magicNumber)));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
            upgradeMagicNumber(UPG_MAGIC);
        }
    }
}