package guardian.relics;

import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import expansioncontent.patches.CenterGridCardSelectScreen;
import guardian.GuardianMod;
import guardian.cards.*;

import java.util.ArrayList;

public class GemstoneGun extends CustomRelic implements CustomSavable<ArrayList<String>> {

    public static final String ID = GuardianMod.makeID("GemstoneGun");

    public static final String IMG_PATH = "relics/GemstoneGun.png";
    public static final String OUTLINE_IMG_PATH = "relics/GemstoneGunOutline.png";
    int numPicked = 0;
    private boolean cardSelected = true;
    private String myGemOne = null;
    private String myGemTwo = null;
    private String myGemThree = null;
    //private String myGemFour = null;
    private boolean allDone = false;

    public GemstoneGun() {
        super(ID, new Texture(GuardianMod.getResourcePath(IMG_PATH)), new Texture(GuardianMod.getResourcePath(OUTLINE_IMG_PATH)), RelicTier.RARE, LandingSound.CLINK);
        allDone = false;
    }

    @Override
    public void onEquip() {
        allDone = false;
        openScreen();
    }

    @Override
    public void atBattleStart() {
        flash();
        GemstoneGunCard c = new GemstoneGunCard();
        c.addGemToSocket((AbstractGuardianCard) CardLibrary.getCopy(myGemOne));
        c.addGemToSocket((AbstractGuardianCard) CardLibrary.getCopy(myGemTwo));
        c.addGemToSocket((AbstractGuardianCard) CardLibrary.getCopy(myGemThree));
       // c.addGemToSocket((AbstractGuardianCard) CardLibrary.getCopy(myGemFour));

        addToBot(new MakeTempCardInHandAction(c));

        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    @Override
    public ArrayList<String> onSave() {
        ArrayList<String> myList = new ArrayList<>();
        myList.add(myGemOne);
        myList.add(myGemTwo);
        myList.add(myGemThree);
      //  myList.add(myGemFour);
        return myList;
    }

    @Override
    public void onLoad(ArrayList<String> abstractCards) {
        myGemOne = abstractCards.get(0);
        myGemTwo = abstractCards.get(1);
        myGemThree = abstractCards.get(2);
       // myGemFour = abstractCards.get(3);
       // setDescriptionAfterLoading();
    }

    public static ArrayList<AbstractCard> getCharacterSafeGems(int count) {
        ArrayList<String> allGemCards = new ArrayList<>();
        ArrayList<AbstractCard> rewardGemCards = new ArrayList<>();

        allGemCards.add("RED");
        allGemCards.add("GREEN");
        allGemCards.add("ORANGE");
        allGemCards.add("CYAN");
        allGemCards.add("WHITE");
        allGemCards.add("CRIMSON");
        allGemCards.add("FRAGMENTED");
        allGemCards.add("PURPLE");
        allGemCards.add("SYNTHETIC");

        int rando;
        String ID;
        for (int i = 0; i < count; i++) {
            rando = AbstractDungeon.cardRng.random(0, allGemCards.size() - 1);
            ID = allGemCards.get(rando);
            switch (ID) {
                case "RED":
                    rewardGemCards.add(new Gem_Red());
                    break;
                case "GREEN":
                    rewardGemCards.add(new Gem_Green());
                    break;
                case "ORANGE":
                    rewardGemCards.add(new Gem_Orange());
                    break;
                case "CYAN":
                    rewardGemCards.add(new Gem_Cyan());
                    break;
                case "WHITE":
                    rewardGemCards.add(new Gem_White());
                    break;
                case "BLUE":
                    rewardGemCards.add(new Gem_Blue());
                    break;
                case "CRIMSON":
                    rewardGemCards.add(new Gem_Crimson());
                    break;
                case "FRAGMENTED":
                    rewardGemCards.add(new Gem_Fragmented());
                    break;
                case "PURPLE":
                    rewardGemCards.add(new Gem_Purple());
                    break;
                case "SYNTHETIC":
                    rewardGemCards.add(new Gem_Synthetic());
                    break;
                case "YELLOW":
                    rewardGemCards.add(new Gem_Yellow());
                    break;
            }
            allGemCards.remove(rando);
        }

        return rewardGemCards;
    }

    private void openScreen() {
        this.cardSelected = false;
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }

        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
        CenterGridCardSelectScreen.centerGridSelect = true;
        CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        ArrayList<AbstractCard> gems = getCharacterSafeGems(4);
        for (AbstractCard q : gems) {
            group.addToTop(q);
        }
        AbstractDungeon.gridSelectScreen.open(group, 1, "Choose a Gem to load the Gem Gun.", false, false, false, false);
    }

    @Override
    public void update() {
        super.update();
        if (!this.cardSelected && AbstractDungeon.gridSelectScreen.selectedCards.size() == 1) {
            this.cardSelected = true;
            CenterGridCardSelectScreen.centerGridSelect = false;

            numPicked++;
            switch (numPicked) {
                case 1:
                    myGemOne = AbstractDungeon.gridSelectScreen.selectedCards.get(0).cardID;
                    break;
                case 2:
                    myGemTwo = AbstractDungeon.gridSelectScreen.selectedCards.get(0).cardID;
                    break;
                case 3:
                    myGemThree = AbstractDungeon.gridSelectScreen.selectedCards.get(0).cardID;
                    break;
                    /*
                case 4:
                    myGemFour = AbstractDungeon.gridSelectScreen.selectedCards.get(0).cardID;
                    break;
                    */
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();

            if (numPicked == 4) {
                allDone = true;
                AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                description = getUpdatedDescription();
            } else {
                openScreen();
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
