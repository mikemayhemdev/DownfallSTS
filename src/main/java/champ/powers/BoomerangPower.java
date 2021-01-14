package champ.powers;

import basemod.helpers.VfxBuilder;
import basemod.interfaces.CloneablePowerInterface;
import champ.ChampMod;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import downfall.util.TextureLoader;

public class BoomerangPower extends AbstractPower implements CloneablePowerInterface, NonStackablePower {

    public static final String POWER_ID = ChampMod.makeID("BoomerangPower");

    private static final Texture tex84 = TextureLoader.getTexture(ChampMod.getModID() + "Resources/images/powers/ReturningCrown84.png");
    private static final Texture tex32 = TextureLoader.getTexture(ChampMod.getModID() + "Resources/images/powers/ReturningCrown32.png");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private AbstractCard stored;

    public BoomerangPower(AbstractCard held) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = -1;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        stored = held;
        this.updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        flash();
        stored.freeToPlayOnce = true;
        AbstractDungeon.effectList.add(new VfxBuilder(TextureLoader.getTexture("champResources/images/relics/ChampionCrown.png"), 0, owner.hb.cY,0.75F)
                .moveX(0, owner.hb.cX)
                .rotate(300F)
                .build());
        // Then it flies back!
        addToBot(new AbstractGameAction() {
            public void update() {
                if (AbstractDungeon.player.drawPile.contains(stored)) {
                    addToBot(new AbstractGameAction() {
                        @Override
                        public void update() {
                            isDone = true;
                            AbstractDungeon.player.drawPile.moveToHand(stored);
                        }
                    });
                } else if (AbstractDungeon.player.discardPile.contains(stored)) {
                    addToBot(new AbstractGameAction() {
                        @Override
                        public void update() {
                            isDone = true;
                            AbstractDungeon.player.discardPile.moveToHand(stored);
                        }
                    });
                } else if (AbstractDungeon.player.exhaustPile.contains(stored)) {
                    addToBot(new AbstractGameAction() {
                        @Override
                        public void update() {
                            isDone = true;
                            AbstractDungeon.player.exhaustPile.moveToHand(stored);
                        }
                    });
                }

                isDone = true;
            }
        });


        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + stored.name + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new BoomerangPower(stored);
    }
}