package theHexaghost.powers;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.GainPowerEffect;
import downfall.util.TextureLoader;
import theHexaghost.HexaMod;

import java.util.ArrayList;

public class CrispyPower_new extends TwoAmountPower {
    public static final String POWER_ID = HexaMod.makeID("CrispyPower_new");

    private static final Texture tex84 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/ExtraCrispy84.png");
    private static final Texture tex32 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/ExtraCrispy32.png");

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static int exhausted_cards_this_turn = 0;
    private float timer;

    public CrispyPower_new(final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        this.amount2 = 2;
        this.type = AbstractPower.PowerType.BUFF;
        this.isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    @Override
    public void update(int slot) {
        super.update(slot);
        int count = 0;
        for(AbstractCard c: AbstractDungeon.player.hand.group){
            if(c.isEthereal){
                count += 1;
            }
        }
        if( (count + exhausted_cards_this_turn) >= 2 ) {
            if (this.timer <= 0F){
                ArrayList<AbstractGameEffect> effect2 = ReflectionHacks.getPrivate(this, AbstractPower.class, "effect");
                effect2.add(new GainPowerEffect(this));
                this.timer = 1F;
            } else {
                this.timer -= Gdx.graphics.getDeltaTime();
            }
        }
    }

    @Override
    public void atStartOfTurn() {
        exhausted_cards_this_turn = 0;
        amount2 = 2;
        updateDescription();
    }

    public void onExhaust(AbstractCard card) {
        exhausted_cards_this_turn += 1;
        amount2 -= 1;
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + (amount>=2 ? DESCRIPTIONS[2] : DESCRIPTIONS[1]);
    }


}
