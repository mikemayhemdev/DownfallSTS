package awakenedOne.relics;

import awakenedOne.AwakenedOneMod;
import awakenedOne.cards.meme.NotConjureBlade;
import awakenedOne.cards.tokens.spells.AbstractSpellCard;
import awakenedOne.cards.tokens.spells.ESPSpell;
import awakenedOne.util.TexLoader;
import basemod.abstracts.CustomRelic;
import basemod.helpers.CardPowerTip;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.ConjureBlade;
import com.megacrit.cardcrawl.cards.tempCards.Expunger;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.cards.SoulRoll;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.getEnemies;

public class CurvedSword extends CustomRelic {

    public static final String ID = AwakenedOneMod.makeID("CurvedSword");
    private static final Texture IMG = TexLoader.getTexture(makeRelicPath("CurvedSword.png")); //TODO: Images
    private static final Texture OUTLINE = TexLoader.getTexture(makeRelicOutlinePath("CurvedSword.png"));

    //You should only be able to obtain this from console. I couldn't help myself from making this joke, sorry.
    //The Expunger goes into your Spell Pile.

    public CurvedSword() {
        super(ID, IMG, OUTLINE, RelicTier.DEPRECATED, LandingSound.CLINK);
        this.tips.add(new CardPowerTip(new ConjureBlade()));
    }

    @Override
    public void atBattleStart() {
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        this.addToTop(new MakeTempCardInHandAction(new NotConjureBlade(), 1, false));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}