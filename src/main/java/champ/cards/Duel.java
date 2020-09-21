package champ.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Duel extends AbstractChampCard {

    public final static String ID = makeID("Duel");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 3;

    private static final int BLOCK = 8;
    private static final int UPG_BLOCK = 3;

    public Duel() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        if (monsterList().size() == 1 && !this.purgeOnUse) {
            AbstractCard r = this;
            atb(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    GameActionManager.queueExtraCard(r, m);
                }
            });
        }
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
        upgradeBlock(UPG_BLOCK);
    }
}