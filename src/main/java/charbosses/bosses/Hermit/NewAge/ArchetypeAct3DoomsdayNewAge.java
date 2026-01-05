package charbosses.bosses.Hermit.NewAge;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Ironclad.ArchetypeBaseIronclad;
import charbosses.cards.curses.EnDoubt;
import charbosses.cards.curses.EnInjury;
import charbosses.cards.hermit.*;
import charbosses.powers.bossmechanicpowers.HermitDoomsday;
import charbosses.relics.*;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.Byrd;
import com.megacrit.cardcrawl.powers.RitualPower;
import downfall.monsters.DoomedDagger;
import downfall.vfx.PotionThrowEffect;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.monsters.city.Byrd.DIALOG;

public class ArchetypeAct3DoomsdayNewAge extends ArchetypeBaseIronclad {
    public boolean froegg = false;
    public ArchetypeAct3DoomsdayNewAge() {
        super("HERMIT_DOOMSDAY_ARCHETYPE", "Doomsday");

        maxHPModifier += 363;
        actNum = 3;
        bossMechanicName = HermitDoomsday.NAME;
        bossMechanicDesc = HermitDoomsday.DESC[0];
    }

    public static AbstractMonster getDoomedSnake(){ // called by EnPurgatory too
        return new DoomedDagger(-400, 200);
    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
        AbstractCreature p = AbstractCharBoss.boss;
        AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(getDoomedSnake(), true));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new HermitDoomsday(p), 1));

        //Cultist Potion!!!
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new PotionThrowEffect("downfallResources/images/vfx/CultistPotion.png", p.hb.cX, p.hb.cY, p.hb.cX, p.hb.cY, 2F, 0.6F, false, true), 0.6F));
            int roll = MathUtils.random(2);
            if (roll == 0) {
                AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_CULTIST_1A"));
            } else if (roll == 1) {
                AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_CULTIST_1B"));
            } else {
                AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_CULTIST_1C"));
            }

        AbstractDungeon.actionManager.addToBottom(new TalkAction(p, Byrd.DIALOG[0], 1.2F, 1.2F));

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RitualPower(p, 1, false), 1));
//        if (!(AbstractDungeon.ascensionLevel >= 19)) {
//            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RitualPower(p, 2, false), 1));
//        } else {
//            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RitualPower(p, 1, false), 1));
//        }
    }

    public void initialize() {

        /////   RELICS   /////
        addRelic(new CBR_NeowsBlessing());
        addRelic(new CBR_CursedKey());
        addRelic(new CBR_CharredGlove());
        addRelic(new CBR_BrassTacks());
        addRelic(new CBR_Omamori());
    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;


        if (!looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnShadowCloak());
                    addToList(cardsList, new EnGrudge(15));
                    addToList(cardsList, new EnGlare());
                    turn++;
                    break;
                case 1:
                    addToList(cardsList, new EnInjury());
                    addToList(cardsList, new EnInjury());
                    addToList(cardsList, new EnDoubt());
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnPurgatory(), extraUpgrades);
                    addToList(cardsList, new EnMalice());
                    addToList(cardsList, new EnSprayNPray());
                    turn = 0;
                    looped = true;
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnInjury());
                    addToList(cardsList, new EnGrudge(17));
                    addToList(cardsList, new EnSprayNPray());
                    turn++;
                    break;
                case 1:
                    EnMalice mal = new EnMalice();
                    addToList(cardsList, mal);
                    addToList(cardsList, new EnGlare());
                    AbstractCard doubt = new EnDoubt();
                    mal.setExhaust(doubt);
                    addToList(cardsList, doubt);
                    turn++;
                    break;
                case 2:
                    addToList(cardsList, new EnPurgatory());
                    addToList(cardsList, new EnDoubt());
                    addToList(cardsList, new EnInjury());
                    turn=0;
                    break;
            }
        }
        return cardsList;
    }

    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_Girya(2));
    }
}