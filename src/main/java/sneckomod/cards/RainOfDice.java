package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import sneckomod.actions.MuddleAction;
import sneckomod.actions.NoApplyRandomDamageAction;

public class RainOfDice extends AbstractSneckoCard {

    public final static String ID = makeID("RainOfDice");

    public RainOfDice() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseSilly = silly = 6;
        baseDamage = 12;
        this.returnToHand = true;
        tags.add(SneckoMod.RNG);
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
        atb(new NoApplyRandomDamageAction(AbstractDungeon.getMonsters().getRandomMonster(true), silly, damage, 1, AbstractGameAction.AttackEffect.BLUNT_LIGHT, this, DamageInfo.DamageType.NORMAL));
        atb(new MuddleAction(this));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeSilly(3);
            upgradeDamage(3);
        }
    }
}