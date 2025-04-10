//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package guardian.actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import guardian.GuardianMod;
import guardian.cards.*;
import guardian.orbs.StasisOrb;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class GemFireAction extends AbstractGameAction {
    private DamageInfo info;
    private float startingDuration;
    private int exhaustCount;
    private ArrayList<GuardianMod.socketTypes> shots = new ArrayList<>();

    public GemFireAction(AbstractCreature target) {
        this.info = info;
        this.setValues(target, info);
        this.actionType = ActionType.WAIT;
        this.attackEffect = AttackEffect.FIRE;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
        this.target = target;
    }

    public static class GemShootEffect extends AbstractGameEffect {

        private GuardianMod.socketTypes gem;
        private float x1, y1, x2, y2, x3, y3, sX, sY, scaleX, scaleY;
        private Color glowColor;
        
        public GemShootEffect(GuardianMod.socketTypes gem, int hitNo, AbstractCreature target, int total) {
            this.gem = gem;
            this.color = Color.WHITE.cpy();
            this.duration = this.startingDuration = Settings.FAST_MODE
                    ? (1.0f + Settings.ACTION_DUR_XFAST * total)
                    : (1.0f + Settings.ACTION_DUR_FAST * total);
            this.duration += hitNo * (Settings.FAST_MODE ? 0.1 : 0.2);
            sX = x1 = AbstractDungeon.player.hb.cX - AbstractDungeon.player.animX;
            sY = y1 = AbstractDungeon.player.hb.cY - AbstractDungeon.player.animY;
            float theta = MathUtils.random(-1.0f, 1.0f);
            x2 = x1 + MathUtils.sin(theta) * 200 * Settings.xScale;
            y2 = y1 + MathUtils.cos(theta) * 200 * Settings.yScale;
            if (target == null) {
                x3 = Settings.WIDTH * 2;
                y3 = Settings.HEIGHT / 2f + MathUtils.random(-100f, 100f) * Settings.yScale;
            } else {
                x3 = target.hb.cX + target.animX;
                y3 = target.hb.cY + target.animY;

            }
            scaleX = scaleY = scale;
            //this.scale = 0.01f;
            glowColor = this.color;
            if(this.gem != null) glowColor = this.gem.color.cpy();

        }
        @Override
        public void render(SpriteBatch sb) {
            sb.setBlendFunction(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            TextureAtlas.AtlasRegion img = ImageMaster.GLOW_SPARK;

            sb.setColor(glowColor);
            sb.draw(img, this.sX - img.packedWidth/2f + (this.duration <= 0.25f ? -20 : 0),
                    this.sY - img.packedHeight/2f,
                    img.packedWidth/2f,  img.packedHeight/2f,
                    img.packedWidth,  img.packedHeight,
                    this.scaleX, this.scaleY, 00f);

            sb.setColor(this.color);
            Texture texture = GuardianMod.gemTextures.get(gem.ordinal());
            sb.draw(texture,
                    this.sX - 25f, this.sY - 25f,
                    25.0F, 25.0F,
                    50.0F, 50.0F,
                    this.scaleX, this.scaleY,
                    this.rotation,
                    0, 0,
                    texture.getWidth(), texture.getHeight(),
                    false, false
                    );

        }

        @Override
        public void update() {
            float deployed = this.startingDuration - 0.05f;

            this.duration -= Gdx.graphics.getDeltaTime();
            this.rotation = this.duration * 1080.0f;
            this.scale = Settings.scale * Math.min(1.0f, Math.max(0.0f, (1.0f - (this.duration - deployed) / 0.25f)));
            this.scaleX = Interpolation.sine.apply(scale * 1.25f, scale * 2f, duration * 4);

            if (this.duration > this.startingDuration) {
                this.sX = this.x1;
                this.sY = this.y1;
            } else if (this.duration > deployed) {
                this.sX = Interpolation.fade.apply(x2, x1, (this.duration - deployed) / 0.25f);
                this.sY = Interpolation.fade.apply(y2, y1, (this.duration - deployed) / 0.25f);
            } else if (this.duration > 0.25f) {
                this.sX = x2; this.sY = y2;
            } else {
                this.rotation = 0f;
                this.scaleX = 1.5f;
                this.sX = Interpolation.pow2Out.apply(x3, x2, (this.duration) * 4);
                this.sY = Interpolation.pow2Out.apply(y3, y2, (this.duration) * 4);
            }
            if (this.duration < 0.0F) {
                this.isDone = true;
                this.color.a = 0.0F;
            }
        }

        @Override
        public void dispose() {

        }
    }

    public class GemFireDamageAction extends AbstractGameAction {

        @Override
        public void update() {
            if (this.duration == this.startDuration) {
                //addToTop(new DamageRandomEnemyAction(info, AttackEffect.SLASH_DIAGONAL));
                this.duration = 0.0f;
                this.isDone = true;
            }
        }
    }

    public int get_cards(){
        addGemsFromGroup(AbstractDungeon.player.hand);
        addGemsFromGroup(AbstractDungeon.player.drawPile);
        addGemsFromGroup(AbstractDungeon.player.discardPile);
        addGemsFromGroup(this.gatherStasisCards());
        return exhaustCount;
    }

    public void update() {

        if (this.duration == this.startingDuration) {
            //addToTop(new GemFireDamageAction());
            addToTop(new AbstractGameAction() {
                { duration = 0.1f; }
                @Override
                public void update() {
                    tickDuration();
                }
            });
            this.get_cards();
            int i = 0;
            for (GuardianMod.socketTypes socket : shots) {
             //   AbstractDungeon.effectsQueue.add(new GemShootEffect(socket, i++, target));
            }

        }
        this.tickDuration();

    }

    public CardGroup gatherStasisCards(){ // Make Gem Cannon exhaust gems from cards in Stasis too.
        CardGroup stasiscards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractOrb o : AbstractDungeon.player.orbs) {
            if (o instanceof StasisOrb) {
                stasiscards.group.add( ((StasisOrb) o).stasisCard );
            }
        }
        return stasiscards;
    }

    private void addGemsFromGroup(CardGroup group) {
        for (AbstractCard c : group.group) {
            if (c instanceof AbstractGuardianCard) {
                AbstractGuardianCard gc = (AbstractGuardianCard) c;
                if (gc.socketCount > 0 || c.hasTag(GuardianMod.GEM)) {
                    if (gc.sockets.size() > 0) {
                        exhaustCount++;
                        exhaustCard(group, c);
                        for (GuardianMod.socketTypes socket : gc.sockets) {
                            if (socket != null) shots.add(socket);
                        }

                    }
                    if (gc.hasTag(GuardianMod.GEM)) {
                        if(gc.belongedOrb!=null) {
                            gc.belongedOrb.cardExhausted = true;
                            AbstractDungeon.actionManager.addToTop(new StasisEvokeIfRoomInHandAction(gc.belongedOrb));
                            gc.belongedOrb = null;
                        }
                        exhaustCount++;
                        exhaustCard(group, c);
                        shots.add(gc.thisGemsType);
                    }
                }
            }
        }
    }

    private void exhaustCard(CardGroup group, AbstractCard c) {
       // addToTop(new ExhaustGemAction(c, group, exhaustCount));
    }

    public class ExhaustGemAction extends AbstractGameAction {
        private AbstractCard card;
        private CardGroup group;
        private int hitNo;

        public ExhaustGemAction(AbstractCard card, CardGroup group, int hitNo) {
            this.card = card;
            this.group = group;
            this.hitNo = hitNo;
            this.duration = this.startDuration = Settings.FAST_MODE ? Settings.ACTION_DUR_XFAST : Settings.ACTION_DUR_FAST;
        }

        @Override
        public void update() {
            CardGroup gemsToBeExhausted =new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            if (this.duration == this.startDuration && this.group.contains(this.card) && card.hasTag(GuardianMod.GEM)) {
                card.current_y = -200.0F * Settings.scale;
                card.target_x = (float)Settings.WIDTH / 2.0F + 200F - 40F * hitNo * Settings.xScale;
                card.target_y = (float)Settings.HEIGHT * .25f;
                card.targetAngle = 0.0F;
                card.lighten(false);
                card.drawScale = 0.12F;
                card.targetDrawScale = 0.75F;
                this.group.moveToExhaustPile(this.card);
                CardCrawlGame.dungeon.checkForPactAchievement();
                this.card.exhaustOnUseOnce = false;
                this.card.freeToPlayOnce = false;
            } else {
                if (this.duration == this.startDuration && this.group.contains(this.card)) {
                    card.current_y = -200.0F * Settings.scale;
                    card.target_x = (float)Settings.WIDTH / 2.0F + 200F - 40F * hitNo * Settings.xScale;
                    card.target_y = (float)Settings.HEIGHT * .25f;
                    card.targetAngle = 0.0F;
                    card.lighten(false);
                    card.drawScale = 0.12F;
                    card.targetDrawScale = 0.75F;
                    for(GuardianMod.socketTypes s : ((AbstractGuardianCard)this.card).sockets){ // Gems in sockets actually go into exhaust pile.
                        AbstractGuardianCard gem;
                        switch (s){
                            case RED:
                                gem = new Gem_Red();
                                gemsToBeExhausted.group.add(gem);
                                gemsToBeExhausted.moveToExhaustPile(gem);
                                break;
                            case GREEN:
                                gem = new Gem_Green();
                                gemsToBeExhausted.group.add(gem);
                                gemsToBeExhausted.moveToExhaustPile(gem);
                                break;
                            case LIGHTBLUE:
                                gem = new Gem_Lightblue();
                                gemsToBeExhausted.group.add(gem);
                                gemsToBeExhausted.moveToExhaustPile(gem);
                                break;
                            case ORANGE:
                                gem = new Gem_Orange();
                                gemsToBeExhausted.group.add(gem);
                                gemsToBeExhausted.moveToExhaustPile(gem);
                                break;
                            case WHITE:
                                gem = new Gem_White();
                                gemsToBeExhausted.group.add(gem);
                                gemsToBeExhausted.moveToExhaustPile(gem);
                                break;
                            case CYAN:
                                gem = new Gem_Cyan();
                                gemsToBeExhausted.group.add(gem);
                                gemsToBeExhausted.moveToExhaustPile(gem);
                                break;
                            case BLUE:
                                gem = new Gem_Blue();
                                gemsToBeExhausted.group.add(gem);
                                gemsToBeExhausted.moveToExhaustPile(gem);
                                break;
                            case CRIMSON:
                                gem = new Gem_Crimson();
                                gemsToBeExhausted.group.add(gem);
                                gemsToBeExhausted.moveToExhaustPile(gem);
                                break;
                            case FRAGMENTED:
                                gem = new Gem_Fragmented();
                                gemsToBeExhausted.group.add(gem);
                                gemsToBeExhausted.moveToExhaustPile(gem);
                                break;
                            case PURPLE:
                                gem = new Gem_Purple();
                                gemsToBeExhausted.group.add(gem);
                                gemsToBeExhausted.moveToExhaustPile(gem);
                                break;
                            case SYNTHETIC:
                                gem = new Gem_Synthetic();
                                gemsToBeExhausted.group.add(gem);
                                gemsToBeExhausted.moveToExhaustPile(gem);
                                break;
                            case YELLOW:
                                gem = new Gem_Yellow();
                                gemsToBeExhausted.group.add(gem);
                                gemsToBeExhausted.moveToExhaustPile(gem);
                                break;
                        }
                    }
                    ((AbstractGuardianCard)card).sockets.clear();
                    ((AbstractGuardianCard)card).updateDescription();
                    CardCrawlGame.dungeon.checkForPactAchievement();
                    this.card.exhaustOnUseOnce = false;
                    this.card.freeToPlayOnce = false;
                }
            }

            this.tickDuration();
        }
    }

}
