package slimebound.powers;


import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.SmokePuffEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.SlimeboundMod;
import slimebound.characters.SlimeboundCharacter;
import slimebound.orbs.SpawnedSlime;
import slimebound.vfx.DoubleSlimeParticle;


public class DuplicatedFormPower extends AbstractPower {
    public static final String POWER_ID = "Slimebound:DuplicatedFormPower";
    public static final String NAME = "Potency";
    public static final String IMG = "powers/DuplicatedEchoS.png";
    public static final Logger logger = LogManager.getLogger(SlimeboundMod.class.getName());
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static String[] DESCRIPTIONS;
    private AbstractCreature source;
    private int cardsDoubledThisTurn = 0;
    private DoubleSlimeParticle VFX;


    public DuplicatedFormPower(AbstractCreature owner, AbstractCreature source, int amount) {

        this.name = NAME;

        this.ID = POWER_ID;


        this.owner = owner;

        this.source = source;


        this.img = new com.badlogic.gdx.graphics.Texture(SlimeboundMod.getResourcePath(IMG));

        this.type = POWER_TYPE;

        this.amount = amount;
        DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;

        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

        updateDescription();

    }

    public void atStartOfTurn() {
        this.cardsDoubledThisTurn = 0;
    }


    public void updateDescription() {


        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = (DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2]);
        }


    }

    public void onInitialApplication() {
        SlimeboundMod.spritealtered = true;
        AbstractPlayer p = AbstractDungeon.player;

        AbstractDungeon.effectsQueue.add(new SmokePuffEffect(p.hb.cX, p.hb.cY));
        VFX = new DoubleSlimeParticle(AbstractDungeon.player);
        AbstractDungeon.actionManager.addToBottom(new VFXAction(VFX));
        if (p instanceof SlimeboundCharacter) {
            SlimeboundCharacter hero = (SlimeboundCharacter) p;
            hero.setRenderscale(1.5F);
        }
        p.hb_x = p.hb_x + (100 * Settings.scale);
        p.drawX = p.drawX - (100 * Settings.scale);
        p.hb.cX = p.hb.cX + (100 * Settings.scale);


    }


    public void onUseCard(AbstractCard card, UseCardAction action) {
        if ((!card.purgeOnUse) && (this.amount > 0) && (card.target == AbstractCard.CardTarget.ENEMY || card.target == AbstractCard.CardTarget.ALL_ENEMY) && this.cardsDoubledThisTurn < this.amount) {
            this.cardsDoubledThisTurn += 1;
            flash();
            AbstractMonster m = null;

            if (action.target != null) {
                m = (AbstractMonster) action.target;
            }

            AbstractCard tmp = card.makeSameInstanceOf();
            AbstractDungeon.player.limbo.addToBottom(tmp);
            tmp.current_x = card.current_x;
            tmp.current_y = card.current_y;
            tmp.target_x = (Settings.WIDTH / 2.0F - 300.0F * Settings.scale);
            tmp.target_y = (Settings.HEIGHT / 2.0F);
            tmp.freeToPlayOnce = true;

            if (m != null) {
                tmp.calculateCardDamage(m);
            }

            tmp.purgeOnUse = true;
            AbstractDungeon.actionManager.cardQueue.add(new com.megacrit.cardcrawl.cards.CardQueueItem(tmp, m, card.energyOnUse));
        }
    }

    public void onDeath() {
        VFX.finish();
    }

    public void onVictory() {
        VFX.finish();
    }
}



