package charbosses.relics;

import charbosses.bosses.AbstractBossDeckArchetype;
import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Ironclad.CharBossIronclad;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.curses.EnRegret;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import evilWithin.EvilWithinMod;

import java.util.ArrayList;


public class CBR_NeowsBlessing extends AbstractCharbossRelic {
    public static String ID = EvilWithinMod.makeID("NeowBlessing");
    private static RelicTier tier = RelicTier.SPECIAL;
    private static LandingSound sound = LandingSound.MAGICAL;
    public String relicName = "";
    private int HP = 0;

    public CBR_NeowsBlessing() {
        super(ID, tier, sound, new Texture(EvilWithinMod.assetPath("images/relics/blessing.png")));
    }

    public void onEquip() {
        this.HP = MathUtils.floor(100 + ((AbstractDungeon.actNum - 1 ) * 60));
        owner.increaseMaxHp(MathUtils.floor(this.HP), false);
        updateDescription(null);
    }


    @Override
    public void updateDescription(final AbstractPlayer.PlayerClass c) {
        this.description = getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    @Override
    public String getUpdatedDescription() {
        if (this.owner != null) {
            return this.DESCRIPTIONS[0] + this.owner.energyString + " .";
        }
        return this.DESCRIPTIONS[0] + "[E] .";
    }


    @Override
    public AbstractRelic makeCopy() {
        return new CBR_NeowsBlessing();
    }
}
