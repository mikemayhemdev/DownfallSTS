package theHexaghost.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theHexaghost.HexaMod;
import downfall.util.TextureLoader;

import static theHexaghost.GhostflameHelper.activeGhostFlame;
import static theHexaghost.HexaMod.renderFlames;

public class CrispyPower_new extends AbstractPower{
    public static final String POWER_ID = HexaMod.makeID("CrispyPower_new");

    private static final Texture tex84 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/ExtraCrispy84.png");
    private static final Texture tex32 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/ExtraCrispy32.png");

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public CrispyPower_new(final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        this.type = AbstractPower.PowerType.BUFF;
        this.isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    public void onExhaust(AbstractCard card) {
        flash();

        for(int i = 0; i < this.amount; i++){
            if (!activeGhostFlame.charged && renderFlames && activeGhostFlame.advanceOnCardUse) {
                activeGhostFlame.advanceTrigger(card);
            }
        }
    }
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + (amount>=2 ? DESCRIPTIONS[2] : DESCRIPTIONS[1]);
    }


}
