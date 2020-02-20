package evilWithin.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.EscapeAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.Chosen;
import com.megacrit.cardcrawl.monsters.exordium.Cultist;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import evilWithin.EvilWithinMod;
import evilWithin.actions.LoseRelicAction;
import evilWithin.actions.SpeechBubbleAction;
import evilWithin.events.WingStatue_Evil;
import slimebound.SlimeboundMod;

public class RedIOU extends CustomRelic {

    public static final String ID = EvilWithinMod.makeID("RedIOU");
    private static final Texture IMG = new Texture(EvilWithinMod.assetPath("images/relics/WingStatue.png"));
    private static final Texture OUTLINE = new Texture(EvilWithinMod.assetPath("images/relics/WingStatue.png"));

    public RedIOU() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss && AbstractDungeon.actNum == 3) {
            flash();
        }
    }

}
