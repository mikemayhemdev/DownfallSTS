//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package guardian.events;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.city.Colosseum;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import guardian.GuardianMod;
import guardian.relics.PickAxe;

import java.util.ArrayList;

public class GemMine extends AbstractImageEvent {
    public static final String ID = "Guardian:GemMine";
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    public static final String[] OPTIONS;
    private static final EventStrings eventStrings;
    private static final String DIALOG_START;
    private static final String DIALOG_LEAVE;
    private static final String DIALOG_MINE;
    private static final String DIALOG_LEAVEWITHGEM;
    private static final String DIALOG_LOOT;
    private static final String DIALOG_MINEPICK;

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString(ID);
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_MINE = DESCRIPTIONS[1];
        DIALOG_LEAVE = DESCRIPTIONS[2];
        DIALOG_START = DESCRIPTIONS[0];
        DIALOG_LEAVEWITHGEM = DESCRIPTIONS[3];
        DIALOG_LOOT = DESCRIPTIONS[4];
        DIALOG_MINEPICK = DESCRIPTIONS[5];
    }

    private int screenNum = 0;
    private boolean tookGems = false;
    private int damage;

    public GemMine() {
        super(NAME, DIALOG_START, GuardianMod.getResourcePath("/events/gemMine.jpg"));
        if (AbstractDungeon.ascensionLevel >= 15) {
            this.damage = 8;
        } else {
            this.damage = 6;
        }
        this.imageEventText.updateBodyText(DIALOG_START);

        if (AbstractDungeon.player.hasRelic(PickAxe.ID)) {
            if (AbstractDungeon.player.getRelic(PickAxe.ID).counter == -2) {
                this.imageEventText.setDialogOption(OPTIONS[5], true);
            } else {
                this.imageEventText.setDialogOption(OPTIONS[4]);
            }
        } else {
            this.imageEventText.setDialogOption(OPTIONS[3], new PickAxe());

        }
        this.imageEventText.setDialogOption(OPTIONS[0] + this.damage + OPTIONS[1]);

        this.imageEventText.setDialogOption(OPTIONS[2]);

    }

    public void onEnterRoom() {
        if (Settings.AMBIANCE_ON) {
            CardCrawlGame.sound.play("EVENT_GOLDEN");
        }

    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screenNum) {
            case 0:
                switch (buttonPressed) {
                    case 0:
                        if (AbstractDungeon.player.hasRelic(PickAxe.ID)) {
                            this.imageEventText.updateBodyText(DIALOG_MINEPICK);
                            ArrayList<AbstractCard> gems = GuardianMod.getRewardGemCards(false, 1);
                            AbstractCard card = gems.get(0);

                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(card, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                            CardCrawlGame.sound.play("MONSTER_BOOK_STAB_0");
                            AbstractDungeon.player.getRelic(PickAxe.ID).onTrigger();
                            if (AbstractDungeon.player.getRelic(PickAxe.ID).counter == -2) {
                                this.imageEventText.updateDialogOption(0, OPTIONS[5], true);

                            }
                            this.tookGems = true;
                        } else {

                            this.screenNum = 0;
                            AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter("Gremlin Gang");
                            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMBAT;
                            AbstractDungeon.getCurrRoom().rewards.clear();
                            AbstractDungeon.getCurrRoom().rewardAllowed = false;
                            enterCombatFromImage();
                            this.imageEventText.updateBodyText(DIALOG_LOOT);
                            this.imageEventText.updateDialogOption(0, OPTIONS[4], false);

                        }

                        return;
                    case 1:
                        this.imageEventText.updateBodyText(DIALOG_MINE);

                        ArrayList<AbstractCard> gems = GuardianMod.getRewardGemCards(false, 1);
                        AbstractCard card = gems.get(0);

                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(card, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                        CardCrawlGame.sound.play("MONSTER_BOOK_STAB_0");
                        AbstractDungeon.player.damage(new DamageInfo(null, this.damage));
                        this.tookGems = true;
                        return;
                    default:
                        if (this.tookGems) {
                            this.imageEventText.updateBodyText(DIALOG_LEAVEWITHGEM);

                        } else {
                            this.imageEventText.updateBodyText(DIALOG_LEAVE);

                        }
                        this.screenNum = 1;
                        this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                        this.imageEventText.clearRemainingOptions();
                        break;
                }
            case 1:
                this.openMap();
                break;
        }

    }

    public void reopen() {
        if (this.screenNum != 1) {
            AbstractDungeon.resetPlayer();
            AbstractDungeon.player.drawX = (com.megacrit.cardcrawl.core.Settings.WIDTH * 0.25F);
            AbstractDungeon.player.preBattlePrep();
            if (!AbstractDungeon.player.hasRelic(PickAxe.ID)) {
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), new guardian.relics.PickAxe());
                AbstractDungeon.commonRelicPool.remove(PickAxe.ID);
            }
            enterImageFromCombat();
        }
    }
}
