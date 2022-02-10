package hermit.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.green.MasterfulStab;
import com.megacrit.cardcrawl.cards.red.BloodForBlood;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import hermit.HermitMod;
import hermit.characters.hermit;
import hermit.patches.EnumPatch;


import java.util.Iterator;

import static hermit.HermitMod.loadJokeCardImage;
import static hermit.HermitMod.makeCardPath;

public class Shortfuse extends AbstractDynamicCard {


    /*
     * SNAPSHOT: Deals 12/16 damage, Dead-On makes it free.
     */


    // TEXT DECLARATION

    public static final String ID = HermitMod.makeID(Shortfuse.class.getSimpleName());
    public static final String IMG = makeCardPath("short_fuse.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = hermit.Enums.COLOR_YELLOW;

    private static final int COST = 3;

    private static final int DAMAGE = 18;
    private static final int UPGRADE_PLUS_DMG = 4;

    private int cost_revert = 0;

    // /STAT DECLARATION/

    public Shortfuse() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        loadJokeCardImage(this, "short_fuse.png");
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new ExplosionSmallEffect(m.hb.cX, m.hb.cY), 0.1F));
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn)));

    }

    @Override
    public void atTurnStart() {
        this.updateCost(this.cost_revert);
        if (this.cost == COST)
            this.isCostModified = false;
        this.cost_revert = 0;
    }

    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if ((c.hasTag(CardTags.STARTER_STRIKE) || c.hasTag(CardTags.STARTER_DEFEND)) && this.costForTurn>0) {
            if (this.cost > 0) {
                this.updateCost(-1);
                this.cost_revert++;
            }
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        AbstractCard tmp = new Shortfuse();
        if (CardCrawlGame.dungeon != null && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            this.setCostForTurn(this.cost - GameActionManager.totalDiscardedThisTurn);
        }

        return tmp;
    }

}