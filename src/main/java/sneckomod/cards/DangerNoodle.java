package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class DangerNoodle extends AbstractSneckoCard {

    public final static String ID = makeID("DangerNoodle");

    //stupid intellij stuff ATTACK, ENEMY, RARE

    private static final int DAMAGE = 9;
    private static final int UPG_DAMAGE = 3;

    public DangerNoodle() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                ArrayList<AbstractCard> myList = new ArrayList<>();
                for (AbstractCard q : p.hand.group) {
                    if (q.color != AbstractDungeon.player.getCardColor()) {
                        myList.add(q);
                    }
                }
                for (AbstractCard q : myList) {
                    att(new ExhaustSpecificCardAction(q, p.hand));
                }
                for (AbstractCard q : myList) {
                    att(new DamageAction(m, makeInfo(), AttackEffect.BLUNT_HEAVY));
                }
            }
        });
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
        }
    }
}