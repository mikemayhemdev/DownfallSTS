package automaton.cards;

import automaton.FunctionHelper;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ForceShield extends AbstractBronzeCard {

    public final static String ID = makeID("ForceShield");

    //stupid intellij stuff skill, self, uncommon

    private static final int BLOCK = 12;
    private static final int UPG_BLOCK = 4;

    public ForceShield() {
        super(ID, 4, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        if (CardCrawlGame.dungeon != null && AbstractDungeon.currMapNode != null) {
            this.configureCostsOnNewCard();
        }
    }

    public void configureCostsOnNewCard() {
        for (int i = 0; i < FunctionHelper.functionsCompiledThisCombat; i++) {
            updateCost(-1);
        }
    }

    public static void decrementShields() {
        for (AbstractCard q : AbstractDungeon.player.drawPile.group) {
            if (q instanceof ForceShield) {
                q.updateCost(-1);
            }
        }
        for (AbstractCard q : AbstractDungeon.player.hand.group) {
            if (q instanceof ForceShield) {
                q.updateCost(-1);
            }
        }
        for (AbstractCard q : AbstractDungeon.player.discardPile.group) {
            if (q instanceof ForceShield) {
                q.updateCost(-1);
            }
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    public void upp() {
        upgradeBlock(UPG_BLOCK);
    }
}