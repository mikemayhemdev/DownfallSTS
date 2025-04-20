package collector.relics;

import basemod.abstracts.CustomRelic;
import basemod.helpers.CardPowerTip;
import collector.CollectorMod;
import com.megacrit.cardcrawl.cards.curses.Necronomicurse;
import downfall.cards.curses.Sapped;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import downfall.util.TextureLoader;
import expansioncontent.cards.AwakenDeath;
import slimebound.characters.SlimeboundCharacter;
import sneckomod.TheSnecko;

import java.util.stream.Collectors;

import static downfall.patches.EvilModeCharacterSelect.evilMode;

public class ForbiddenFruit extends CustomRelic {
    public static final String ID = CollectorMod.makeID(ForbiddenFruit.class.getSimpleName());
    private static final String IMG_PATH = ForbiddenFruit.class.getSimpleName() + ".png";
    private static final String OUTLINE_IMG_PATH = ForbiddenFruit.class.getSimpleName() + ".png";

    private int stage = 2;
    private boolean selected = true;

    public ForbiddenFruit() {
        super(ID, TextureLoader.getTexture(CollectorMod.makeRelicPath(IMG_PATH)), TextureLoader.getTexture(CollectorMod.makeRelicOutlinePath(OUTLINE_IMG_PATH)), RelicTier.BOSS, LandingSound.MAGICAL);
        tips.add(new CardPowerTip( new Sapped() ) );
    }

    public void onEquip() {
        stage = 0;
        selected = false;

        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
        CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        group.group.addAll(AbstractDungeon.srcRareCardPool.group.stream().map(AbstractCard::makeCopy).collect(Collectors.toList()));
        for(AbstractCard c: group.group){
            for (AbstractRelic r : AbstractDungeon.player.relics) {
                r.onPreviewObtainCard(c);
            }
        }
        AbstractDungeon.gridSelectScreen.open(group, 1, DESCRIPTIONS[1], false, false, false, false);
    }

    @Override
    public void update() {
        super.update();

        if (!selected && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            selected = true;
            AbstractCard card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(card, (float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();

            if (stage == 2) {
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Sapped(), (float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            }
            else if (stage == 0) {
                selected = false;
                CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                for(AbstractCard c: group.group){
                    for (AbstractRelic r : AbstractDungeon.player.relics) {
                        r.onPreviewObtainCard(c);
                    }
                }
                group.group.addAll(AbstractDungeon.srcUncommonCardPool.group.stream().map(AbstractCard::makeCopy).collect(Collectors.toList()));
                AbstractDungeon.gridSelectScreen.open(group, 1, DESCRIPTIONS[2], false, false, false, false);
                stage++;
            }
            else if (stage == 1) {
                selected = false;
                CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                for(AbstractCard c: group.group){
                    for (AbstractRelic r : AbstractDungeon.player.relics) {
                        r.onPreviewObtainCard(c);
                    }
                }
                group.group.addAll(AbstractDungeon.srcCommonCardPool.group.stream().map(AbstractCard::makeCopy).collect(Collectors.toList()));
                AbstractDungeon.gridSelectScreen.open(group, 1, DESCRIPTIONS[3], false, false, false, false);
                stage++;
            }
        }
    }

    public boolean canSpawn() {
        if (AbstractDungeon.player instanceof TheSnecko) {
            return false;
        }

        return ((AbstractDungeon.floorNum > 1)); // you cannot boss swap into forbidden fruit
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
