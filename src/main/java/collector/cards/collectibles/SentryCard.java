package collector.cards.collectibles;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToEnemy;
import static collector.util.Wiz.forAllMonstersLiving;

public class SentryCard extends AbstractCollectibleCard {
    public final static String ID = makeID(SentryCard.class.getSimpleName());
    // intellij stuff attack, enemy, uncommon, 8, 2, , , 2, 1

    public SentryCard() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 8;
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //TODO: Vfx
        dmg(m, AbstractGameAction.AttackEffect.NONE);
        forAllMonstersLiving(q -> applyToEnemy(q, new WeakPower(q, magicNumber, false)));

        for (int i = 0; i < AbstractDungeon.player.exhaustPile.group.stream().filter(q -> q instanceof SentryCard).count(); i++) {
            dmg(m, AbstractGameAction.AttackEffect.NONE);
            forAllMonstersLiving(q -> applyToEnemy(q, new WeakPower(q, magicNumber, false)));
        }
    }

    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(1);
    }
}