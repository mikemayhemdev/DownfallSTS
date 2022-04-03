package timeeater.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.util.Wiz;

import static timeeater.TimeEaterMod.makeID;

public class Paradox extends AbstractTimeEaterCard {
    public final static String ID = makeID("Paradox");
    // intellij stuff attack, self_and_enemy, uncommon, 24, 4, 18, 4, , 

    private boolean blockFake;
    private boolean exposed = false;
    private static String THIS_EFFECT_WILL_ACTIVATE_COLOR = "[#5ebf2a]";

    public Paradox() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 24;
        baseBlock = 18;
        if (Wiz.isInCombat()) {
            blockFake = AbstractDungeon.cardRandomRng.randomBoolean();
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (blockFake) {
            dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        } else {
            blck();
        }
        if (!exposed) {
            exposed = true;
            String[] items = rawDescription.split(" NL ");
            if (blockFake) {
                items[1] = THIS_EFFECT_WILL_ACTIVATE_COLOR + items[1] + "[]";
            } else {
                items[0] = THIS_EFFECT_WILL_ACTIVATE_COLOR + items[0] + "[]";
            }
            StringBuilder sb = new StringBuilder();
            for (String s : items) {
                sb.append(s);
            }
            rawDescription = sb.toString();
        }
    }

    public void upp() {
        upgradeDamage(4);
        upgradeBlock(4);
    }
}