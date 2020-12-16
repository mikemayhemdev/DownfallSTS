package twins.cards;

import twins.actions.FireCardAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Goto extends AbstractTwinsCard {

    public final static String ID = makeID("Goto");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 14;
    private static final int UPG_DAMAGE = 4;

    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 1;

    public Goto() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        atb(new ScryAction(magicNumber));
        atb(new AbstractGameAction() {
            @java.lang.Override
            public void update() {
                isDone = true;
                if (!AbstractDungeon.player.drawPile.isEmpty()) {
                    att(new FireCardAction(AbstractDungeon.player.drawPile.getTopCard(), AbstractDungeon.player.drawPile)); //TODO: Reshuffle if empty instead
                }
            }
        });
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
        upgradeMagicNumber(UPG_MAGIC);
    }
}