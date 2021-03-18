package expansioncontent.powers;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.ScreenOnFireEffect;
import expansioncontent.expansionContentMod;
import downfall.util.TextureLoader;


public class PretendHexWheelPower extends TwoAmountPower implements NonStackablePower {
    public static final String POWER_ID = expansionContentMod.makeID("PretendHexWheelPower");
    public static final String NAME = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).NAME;
    public static final String DESCRIPTIONS[] = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(expansionContentMod.getModID() + "Resources/images/powers/StudyHexaghost84.png");
    private static final Texture tex32 = TextureLoader.getTexture(expansionContentMod.getModID() + "Resources/images/powers/StudyHexaghost32.png");

    public PretendHexWheelPower(int amount, int damage) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        this.amount2 = damage;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();

    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        addToBot(new ReducePowerAction(owner, owner, this, 1));
        if (amount == 1) {
            addToBot(new VFXAction(AbstractDungeon.player, new ScreenOnFireEffect(), 1.0F));
            for (int i = 0; i < 6; i++) {
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        isDone = true;
                        AbstractMonster m = AbstractDungeon.getRandomMonster();
                        if (m != null && !m.halfDead) {
                            addToTop(new DamageAction(m, new DamageInfo(owner, amount2, DamageInfo.DamageType.THORNS), AttackEffect.FIRE));
                        }
                    }
                });
            }
        }
    }

    @Override
    public void updateDescription() {
        description = amount == 1 ? DESCRIPTIONS[2] + amount2 + DESCRIPTIONS[3] :
                DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount2 + DESCRIPTIONS[3];
    }

}




