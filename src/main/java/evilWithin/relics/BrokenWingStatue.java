package evilWithin.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.EscapeAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.Chosen;
import com.megacrit.cardcrawl.monsters.exordium.Cultist;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import evilWithin.EvilWithinMod;
import evilWithin.actions.LoseRelicAction;
import evilWithin.actions.SpeechBubbleAction;
import evilWithin.events.WingStatue_Evil;
import slimebound.SlimeboundMod;

import java.util.ArrayList;

public class BrokenWingStatue extends CustomRelic {

    public static final String ID = EvilWithinMod.makeID("BrokenWingStatue");
    private static final Texture IMG = new Texture(EvilWithinMod.assetPath("images/relics/WingStatue.png"));
    private static final Texture OUTLINE = new Texture(EvilWithinMod.assetPath("images/relics/WingStatue.png"));

    private static final String[] DIALOG = CardCrawlGame.languagePack.getEventString(WingStatue_Evil.ID).DESCRIPTIONS;



    public BrokenWingStatue() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        AbstractMonster receiver = null;
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters){
            if (m instanceof Cultist || m instanceof Chosen){
                receiver = m;
                SlimeboundMod.logger.info("found valid target");
                break;
            }
        }
        if (receiver != null){
            int DialogIndex;
            if (receiver instanceof Cultist){
                DialogIndex = 4;
                SlimeboundMod.logger.info("assigned dialog index 4");
            } else {
                DialogIndex = 6;
                SlimeboundMod.logger.info("assigned dialog index 6");
            }
            this.flash();
            forceWait(5);
            addToBot(new RelicAboveCreatureAction(receiver,this));
            addToBot(new SpeechBubbleAction(DIALOG[DialogIndex], receiver, 2F));
            forceWait(10);
            addToBot(new SpeechBubbleAction(DIALOG[DialogIndex + 1], receiver, 2F));
            forceWait(7);
            AbstractDungeon.actionManager.addToBottom(new LoseRelicAction(this.relicId));
            AbstractDungeon.actionManager.addToBottom(new EscapeAction(receiver));

        }
    }

    private void forceWait(int num){
        for (int i = 0; i < num; i++) {

            addToBot(new WaitAction(0.1F));
        }
    }
}
