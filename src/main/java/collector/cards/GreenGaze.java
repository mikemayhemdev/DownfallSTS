package collector.cards;

import collector.cards.collectibles.AbstractCollectibleCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;

public class GreenGaze extends AbstractCollectorCard {
    public final static String ID = makeID(GreenGaze.class.getSimpleName());
    // intellij stuff attack, enemy, common, 8, 2, 8, 2, , 

    public GreenGaze() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 8;
        baseBlock = 8;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        if (AbstractDungeon.actionManager.cardsPlayedThisTurn.stream().anyMatch(q -> q instanceof AbstractCollectibleCard)) {
            blck();
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = AbstractDungeon.actionManager.cardsPlayedThisTurn.stream().anyMatch(q -> q instanceof AbstractCollectibleCard) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        upgradeDamage(2);
        upgradeBlock(2);
    }
}