package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CheapShot extends AbstractChampCard {

    public final static String ID = makeID("CheapShot");

    //stupid intellij stuff attack, all_enemy, common

    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 2;

    public CheapShot() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        tags.add(ChampMod.FINISHER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        finisher();
        AbstractMonster q = AbstractDungeon.getRandomMonster();
        dmg(q, AbstractGameAction.AttackEffect.FIRE);
        applyToEnemy(q, autoVuln(q, 1));
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                int x = 0;
                for (AbstractCard q : p.hand.group) if (q.hasTag(ChampMod.TECHNIQUE)) x++;
                for (int i = 0; i < x; i++) {
                    AbstractMonster q = AbstractDungeon.getRandomMonster();
                    att(new ApplyPowerAction(q, p, autoVuln(q, 1), 1));
                    att(new DamageAction(q, makeInfo(), AttackEffect.FIRE));
                }
            }
        });
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}